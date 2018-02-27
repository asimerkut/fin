import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DefRelationComponent } from './def-relation.component';
import { DefRelationDetailComponent } from './def-relation-detail.component';
import { DefRelationPopupComponent } from './def-relation-dialog.component';
import { DefRelationDeletePopupComponent } from './def-relation-delete-dialog.component';

export const defRelationRoute: Routes = [
    {
        path: 'def-relation',
        component: DefRelationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefRelations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'def-relation/:id',
        component: DefRelationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const defRelationPopupRoute: Routes = [
    {
        path: 'def-relation-new',
        component: DefRelationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-relation/:id/edit',
        component: DefRelationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'def-relation/:id/delete',
        component: DefRelationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
