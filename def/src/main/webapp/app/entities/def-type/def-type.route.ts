import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DefTypeComponent } from './def-type.component';
import { DefTypeDetailComponent } from './def-type-detail.component';
import { DefTypePopupComponent } from './def-type-dialog.component';
import { DefTypeDeletePopupComponent } from './def-type-delete-dialog.component';

export const defTypeRoute: Routes = [
    {
        path: 'def-type',
        component: DefTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'def-type/:id',
        component: DefTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const defTypePopupRoute: Routes = [
    {
        path: 'def-type-new',
        component: DefTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-type/:id/edit',
        component: DefTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-type/:id/delete',
        component: DefTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
