import request from 'superagent';
import { Promise } from 'es6-promise';

/**
 * Wrapper for calling a API
 */
export const Api = {
    get: function (url) {
        return new Promise(function (resolve, reject) {
            request.get(url)
                // .set('Accept', 'application/json')
                .end(function (err, res) {
                    /* eslint-disable no-console*/
                    if (err) return console.error(err);

                    if (res.status === 404) {
                        reject();
                    } else {
                        resolve(JSON.parse(res.text));
                    }
                });
        });
    }
};
