var gulp = require('gulp');
var ts = require('gulp-typescript');
var typescript = require('typescript');
var runSequence = require('run-sequence');
var sourcemaps = require('gulp-sourcemaps');
var connect = require('gulp-connect');
var open = require('gulp-open');
var sass = require('gulp-sass');
var plumber = require('gulp-plumber');
var KarmaServer = require('karma').Server;
var rimraf = require('gulp-rimraf');
var history = require('connect-history-api-fallback');

var serverOptions = {
  root: '',
  port: 8000,
  livereload: true,
  middleware: function(connect, opt) {
    return [history({})];
  }
};

var srcPath = 'src/app';
var distPath = 'dist/app';
var testPath = 'src/test';
var testDistPath = 'dist';

var tasks = {
  defaultTask: 'default',
  typeScript: 'typeScript-compile',
  clean: 'clean',
  buildSass: 'build-sass',
  startWebServer: 'start-webServer',
  openBrowser: 'open-browser',
  reload: 'reload',
  watch: 'watch',
  watcherRebuild: 'watcher-rebuild',
  testBuild: 'test-build',
  test: 'test',
  staticFiles: 'html'
};

var tsProject = ts.createProject('tsconfig.json', {
  typescript: typescript
});


// Defaults task
gulp.task(tasks.defaultTask, function() {
  runSequence(
    tasks.clean, [tasks.typeScript, tasks.buildSass, tasks.staticFiles],
    tasks.startWebServer,
    tasks.openBrowser,
    tasks.watch
  );
  return gulp.src([
      './index.html'
    ])
    .pipe(gulp.dest('./dist'));
});

// Watches files changes
gulp.task(tasks.watcherRebuild, function(callback) {
  runSequence(
    [tasks.typeScript, tasks.buildSass, tasks.staticFiles],
    tasks.reload
  );
  callback();
});

// Compiles *.ts files by tsconfig.json file and creates sourcemap filse
gulp.task(tasks.typeScript, function() {
  return gulp.src([
      'typings/browser.d.ts', // To avoid add this line into bootstrap.ts file or all other ts files.
      srcPath + '/**/**.ts'
    ])
    .pipe(sourcemaps.init())
    .pipe(ts(tsProject))
    .pipe(sourcemaps.write('./', {
      includeContent: false,
      sourceRoot: '/' + srcPath
    }))
    .pipe(gulp.dest(distPath));
});

// Copies html and css files to dist folder
gulp.task(tasks.staticFiles, function() {
  return gulp.src([
      srcPath + '/**/**.html',
      srcPath + '/**/**.css'
    ])
    .pipe(gulp.dest(distPath));
});

// Builds Sass files into css files
gulp.task(tasks.buildSass, function() {
  return gulp.src(srcPath + '/**/*.scss')
    .pipe(plumber())
    .pipe(sass())
    .pipe(plumber.stop())
    .pipe(gulp.dest(distPath));
});

// Reloads the browser
gulp.task(tasks.reload, function() {
  gulp.src(srcPath)
    .pipe(connect.reload());
});

// Sets watcher to the files
gulp.task(tasks.watch, function() {
  gulp.watch([
    '/index.html',
    srcPath + '/**/**.ts',
    srcPath + '/**/**.html',
    srcPath + '/**/**.scss'
  ], [tasks.watcherRebuild]);
});

// Starts web server
gulp.task(tasks.startWebServer, function() {
  connect.server(serverOptions);
});

// Opens browser
gulp.task(tasks.openBrowser, function() {
  gulp.src('./index.html')
    .pipe(open({
      uri: 'http://localhost:' + serverOptions.port
    }));
});

// Removes dist folder and files in it or subdirectories
gulp.task(tasks.clean, function() {
  return gulp.src([
      'dist/**/**.*'
    ])
    .pipe(rimraf());
});

// Tests
gulp.task(tasks.testBuild, function() {
  return gulp.src([
      'typings/browser.d.ts',
      testPath + '/**/**.ts'
    ])
    .pipe(sourcemaps.init())
    .pipe(ts(tsProject))
    .pipe(sourcemaps.write('./', {
      includeContent: false,
      sourceRoot: '/' + testPath
    }))
    .pipe(gulp.dest(testDistPath));
});

/**
 * @name coverage
 * @description Generates and shows the code coverage report
 */
gulp.task('test:coverage', [tasks.testBuild], function(done) {
  new KarmaServer({
    configFile: __dirname + '/karma.conf.js',
    action: 'run',
    singleRun: true,
    browsers: ['Chrome'],
    coverageReporter: {
      reporters: [{
        type: 'json',
        subdir: '.',
        file: 'coverage-final.json'
      }]
    },
    preprocessors: {
      'dist/**/!(*spec).js': ['coverage']
    },
    reporters: ['progress', 'coverage']
  }, done).on('error', function(err) {
    throw err;
  }).start();
});

gulp.task(tasks.test, ['test:coverage'], function() {
  return gulp.src('./coverage/js/index.html')
    .pipe(open());
});