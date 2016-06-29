import { HomeComponent } from '../home/home.component';
import { InventoryComponent } from '../inventory/inventory.component';
import { ProductEditComponent } from '../inventory/product-edit.component';

export var APP_FS_ROUTES: any[] = [
    { path: '/', name: 'Home', component: HomeComponent },
    { path: '/inventory', name: 'Inventory', component: InventoryComponent, as: 'fishes'},
    { path: '/productEdit/:id', component: ProductEditComponent }
];
