import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopFinansalHareketComponent } from './hop-finansal-hareket.component';
import { HopFinansalHareketDetailComponent } from './hop-finansal-hareket-detail.component';
import { HopFinansalHareketPopupComponent } from './hop-finansal-hareket-dialog.component';
import { HopFinansalHareketDeletePopupComponent } from './hop-finansal-hareket-delete-dialog.component';

export const hopFinansalHareketRoute: Routes = [
    {
        path: 'hop-finansal-hareket',
        component: HopFinansalHareketComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHarekets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-finansal-hareket/:id',
        component: HopFinansalHareketDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHarekets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopFinansalHareketPopupRoute: Routes = [
    {
        path: 'hop-finansal-hareket-new',
        component: HopFinansalHareketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHarekets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-finansal-hareket/:id/edit',
        component: HopFinansalHareketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHarekets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-finansal-hareket/:id/delete',
        component: HopFinansalHareketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHarekets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
