import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FinansalHareketDetayComponent } from './finansal-hareket-detay.component';
import { FinansalHareketDetayDetailComponent } from './finansal-hareket-detay-detail.component';
import { FinansalHareketDetayPopupComponent } from './finansal-hareket-detay-dialog.component';
import { FinansalHareketDetayDeletePopupComponent } from './finansal-hareket-detay-delete-dialog.component';

@Injectable()
export class FinansalHareketDetayResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const finansalHareketDetayRoute: Routes = [
    {
        path: 'finansal-hareket-detay',
        component: FinansalHareketDetayComponent,
        resolve: {
            'pagingParams': FinansalHareketDetayResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'finansal-hareket-detay/:id',
        component: FinansalHareketDetayDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const finansalHareketDetayPopupRoute: Routes = [
    {
        path: 'finansal-hareket-detay-new',
        component: FinansalHareketDetayPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'finansal-hareket-detay/:id/edit',
        component: FinansalHareketDetayPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'finansal-hareket-detay/:id/delete',
        component: FinansalHareketDetayDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHareketDetays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
