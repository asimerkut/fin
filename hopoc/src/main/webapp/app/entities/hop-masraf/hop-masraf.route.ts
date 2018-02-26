import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopMasrafComponent } from './hop-masraf.component';
import { HopMasrafDetailComponent } from './hop-masraf-detail.component';
import { HopMasrafPopupComponent } from './hop-masraf-dialog.component';
import { HopMasrafDeletePopupComponent } from './hop-masraf-delete-dialog.component';

export const hopMasrafRoute: Routes = [
    {
        path: 'hop-masraf',
        component: HopMasrafComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopMasrafs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-masraf/:id',
        component: HopMasrafDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopMasrafs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopMasrafPopupRoute: Routes = [
    {
        path: 'hop-masraf-new',
        component: HopMasrafPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopMasrafs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-masraf/:id/edit',
        component: HopMasrafPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopMasrafs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-masraf/:id/delete',
        component: HopMasrafDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopMasrafs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
