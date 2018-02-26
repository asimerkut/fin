import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopDosyaBorcComponent } from './hop-dosya-borc.component';
import { HopDosyaBorcDetailComponent } from './hop-dosya-borc-detail.component';
import { HopDosyaBorcPopupComponent } from './hop-dosya-borc-dialog.component';
import { HopDosyaBorcDeletePopupComponent } from './hop-dosya-borc-delete-dialog.component';

export const hopDosyaBorcRoute: Routes = [
    {
        path: 'hop-dosya-borc',
        component: HopDosyaBorcComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-dosya-borc/:id',
        component: HopDosyaBorcDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopDosyaBorcPopupRoute: Routes = [
    {
        path: 'hop-dosya-borc-new',
        component: HopDosyaBorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dosya-borc/:id/edit',
        component: HopDosyaBorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dosya-borc/:id/delete',
        component: HopDosyaBorcDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
