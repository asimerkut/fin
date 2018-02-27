import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PerSubmitComponent } from './per-submit.component';
import { PerSubmitDetailComponent } from './per-submit-detail.component';
import { PerSubmitPopupComponent } from './per-submit-dialog.component';
import { PerSubmitDeletePopupComponent } from './per-submit-delete-dialog.component';

export const perSubmitRoute: Routes = [
    {
        path: 'per-submit',
        component: PerSubmitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerSubmits'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'per-submit/:id',
        component: PerSubmitDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerSubmits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const perSubmitPopupRoute: Routes = [
    {
        path: 'per-submit-new',
        component: PerSubmitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerSubmits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-submit/:id/edit',
        component: PerSubmitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerSubmits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'per-submit/:id/delete',
        component: PerSubmitDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PerSubmits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
