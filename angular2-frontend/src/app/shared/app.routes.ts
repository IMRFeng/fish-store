import { HomeComponent } from '../home/home.component';
import { InventoryComponent } from '../inventory/inventory.component';

export var APP_FS_ROUTES: any[] = [
    { path: '/', name: 'Home', component: HomeComponent },
    { path: '/inventory', name: 'Inventory', component: InventoryComponent}
];
