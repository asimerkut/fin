import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DefPivotComponent } from './def-pivot.component';
import { DefPivotDetailComponent } from './def-pivot-detail.component';
import { DefPivotPopupComponent } from './def-pivot-dialog.component';
import { DefPivotDeletePopupComponent } from './def-pivot-delete-dialog.component';

export const defPivotRoute: Routes = [
    {
        path: 'def-pivot',
        component: DefPivotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefPivots'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'def-pivot/:id',
        component: DefPivotDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefPivots'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const defPivotPopupRoute: Routes = [
    {
        path: 'def-pivot-new',
        component: DefPivotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefPivots'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-pivot/:id/edit',
        component: DefPivotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefPivots'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-pivot/:id/delete',
        component: DefPivotDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefPivots'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
