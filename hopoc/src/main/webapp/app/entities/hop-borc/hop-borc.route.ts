import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopBorcComponent } from './hop-borc.component';
import { HopBorcDetailComponent } from './hop-borc-detail.component';
import { HopBorcPopupComponent } from './hop-borc-dialog.component';
import { HopBorcDeletePopupComponent } from './hop-borc-delete-dialog.component';

export const hopBorcRoute: Routes = [
    {
        path: 'hop-borc',
        component: HopBorcComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopBorcs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-borc/:id',
        component: HopBorcDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopBorcs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopBorcPopupRoute: Routes = [
    {
        path: 'hop-borc-new',
        component: HopBorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-borc/:id/edit',
        component: HopBorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-borc/:id/delete',
        component: HopBorcDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
