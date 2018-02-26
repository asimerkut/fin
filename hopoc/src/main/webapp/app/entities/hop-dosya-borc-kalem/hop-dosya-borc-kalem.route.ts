import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopDosyaBorcKalemComponent } from './hop-dosya-borc-kalem.component';
import { HopDosyaBorcKalemDetailComponent } from './hop-dosya-borc-kalem-detail.component';
import { HopDosyaBorcKalemPopupComponent } from './hop-dosya-borc-kalem-dialog.component';
import { HopDosyaBorcKalemDeletePopupComponent } from './hop-dosya-borc-kalem-delete-dialog.component';

export const hopDosyaBorcKalemRoute: Routes = [
    {
        path: 'hop-dosya-borc-kalem',
        component: HopDosyaBorcKalemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-dosya-borc-kalem/:id',
        component: HopDosyaBorcKalemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopDosyaBorcKalemPopupRoute: Routes = [
    {
        path: 'hop-dosya-borc-kalem-new',
        component: HopDosyaBorcKalemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dosya-borc-kalem/:id/edit',
        component: HopDosyaBorcKalemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dosya-borc-kalem/:id/delete',
        component: HopDosyaBorcKalemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
