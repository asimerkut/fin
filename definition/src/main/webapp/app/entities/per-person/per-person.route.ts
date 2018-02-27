import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PerPersonComponent } from './per-person.component';
import { PerPersonDetailComponent } from './per-person-detail.component';
import { PerPersonPopupComponent } from './per-person-dialog.component';
import { PerPersonDeletePopupComponent } from './per-person-delete-dialog.component';

export const perPersonRoute: Routes = [
    {
        path: 'per-person',
        component: PerPersonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPeople'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'per-person/:id',
        component: PerPersonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPeople'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const perPersonPopupRoute: Routes = [
    {
        path: 'per-person-new',
        component: PerPersonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-person/:id/edit',
        component: PerPersonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-person/:id/delete',
        component: PerPersonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
