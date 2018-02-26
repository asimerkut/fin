import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BorcTipiComponent } from './borc-tipi.component';
import { BorcTipiDetailComponent } from './borc-tipi-detail.component';
import { BorcTipiPopupComponent } from './borc-tipi-dialog.component';
import { BorcTipiDeletePopupComponent } from './borc-tipi-delete-dialog.component';

@Injectable()
export class BorcTipiResolvePagingParams implements Resolve<any> {

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

export const borcTipiRoute: Routes = [
    {
        path: 'borc-tipi',
        component: BorcTipiComponent,
        resolve: {
            'pagingParams': BorcTipiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcTipis'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'borc-tipi/:id',
        component: BorcTipiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcTipis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const borcTipiPopupRoute: Routes = [
    {
        path: 'borc-tipi-new',
        component: BorcTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc-tipi/:id/edit',
        component: BorcTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc-tipi/:id/delete',
        component: BorcTipiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
