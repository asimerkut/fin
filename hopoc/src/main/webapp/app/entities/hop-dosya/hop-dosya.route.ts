import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopDosyaComponent } from './hop-dosya.component';
import { HopDosyaDetailComponent } from './hop-dosya-detail.component';
import { HopDosyaPopupComponent } from './hop-dosya-dialog.component';
import { HopDosyaDeletePopupComponent } from './hop-dosya-delete-dialog.component';

export const hopDosyaRoute: Routes = [
    {
        path: 'hop-dosya',
        component: HopDosyaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-dosya/:id',
        component: HopDosyaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopDosyaPopupRoute: Routes = [
    {
        path: 'hop-dosya-new',
        component: HopDosyaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dosya/:id/edit',
        component: HopDosyaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-dosya/:id/delete',
        component: HopDosyaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopDosyas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
