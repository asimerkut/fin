import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PerCompanyComponent } from './per-company.component';
import { PerCompanyDetailComponent } from './per-company-detail.component';
import { PerCompanyPopupComponent } from './per-company-dialog.component';
import { PerCompanyDeletePopupComponent } from './per-company-delete-dialog.component';

export const perCompanyRoute: Routes = [
    {
        path: 'per-company',
        component: PerCompanyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerCompanies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'per-company/:id',
        component: PerCompanyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerCompanies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const perCompanyPopupRoute: Routes = [
    {
        path: 'per-company-new',
        component: PerCompanyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerCompanies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-company/:id/edit',
        component: PerCompanyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerCompanies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-company/:id/delete',
        component: PerCompanyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerCompanies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
