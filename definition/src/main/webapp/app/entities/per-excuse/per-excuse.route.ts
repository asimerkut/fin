import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PerExcuseComponent } from './per-excuse.component';
import { PerExcuseDetailComponent } from './per-excuse-detail.component';
import { PerExcusePopupComponent } from './per-excuse-dialog.component';
import { PerExcuseDeletePopupComponent } from './per-excuse-delete-dialog.component';

export const perExcuseRoute: Routes = [
    {
        path: 'per-excuse',
        component: PerExcuseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerExcuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'per-excuse/:id',
        component: PerExcuseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerExcuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const perExcusePopupRoute: Routes = [
    {
        path: 'per-excuse-new',
        component: PerExcusePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerExcuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-excuse/:id/edit',
        component: PerExcusePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerExcuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-excuse/:id/delete',
        component: PerExcuseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerExcuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
