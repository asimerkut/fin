import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PerPlanComponent } from './per-plan.component';
import { PerPlanDetailComponent } from './per-plan-detail.component';
import { PerPlanPopupComponent } from './per-plan-dialog.component';
import { PerPlanDeletePopupComponent } from './per-plan-delete-dialog.component';

export const perPlanRoute: Routes = [
    {
        path: 'per-plan',
        component: PerPlanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPlans'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'per-plan/:id',
        component: PerPlanDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPlans'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const perPlanPopupRoute: Routes = [
    {
        path: 'per-plan-new',
        component: PerPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPlans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-plan/:id/edit',
        component: PerPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPlans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-plan/:id/delete',
        component: PerPlanDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerPlans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
