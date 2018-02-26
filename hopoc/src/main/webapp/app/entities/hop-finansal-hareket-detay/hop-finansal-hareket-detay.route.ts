import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HopFinansalHareketDetayComponent } from './hop-finansal-hareket-detay.component';
import { HopFinansalHareketDetayDetailComponent } from './hop-finansal-hareket-detay-detail.component';
import { HopFinansalHareketDetayPopupComponent } from './hop-finansal-hareket-detay-dialog.component';
import { HopFinansalHareketDetayDeletePopupComponent } from './hop-finansal-hareket-detay-delete-dialog.component';

export const hopFinansalHareketDetayRoute: Routes = [
    {
        path: 'hop-finansal-hareket-detay',
        component: HopFinansalHareketDetayComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hop-finansal-hareket-detay/:id',
        component: HopFinansalHareketDetayDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hopFinansalHareketDetayPopupRoute: Routes = [
    {
        path: 'hop-finansal-hareket-detay-new',
        component: HopFinansalHareketDetayPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-finansal-hareket-detay/:id/edit',
        component: HopFinansalHareketDetayPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hop-finansal-hareket-detay/:id/delete',
        component: HopFinansalHareketDetayDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HopFinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
