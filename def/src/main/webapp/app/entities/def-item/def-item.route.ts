import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DefItemComponent } from './def-item.component';
import { DefItemDetailComponent } from './def-item-detail.component';
import { DefItemPopupComponent } from './def-item-dialog.component';
import { DefItemDeletePopupComponent } from './def-item-delete-dialog.component';

export const defItemRoute: Routes = [
    {
        path: 'def-item',
        component: DefItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefItems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'def-item/:id',
        component: DefItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const defItemPopupRoute: Routes = [
    {
        path: 'def-item-new',
        component: DefItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-item/:id/edit',
        component: DefItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-item/:id/delete',
        component: DefItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
