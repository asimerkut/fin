import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MasrafTipiComponent } from './masraf-tipi.component';
import { MasrafTipiDetailComponent } from './masraf-tipi-detail.component';
import { MasrafTipiPopupComponent } from './masraf-tipi-dialog.component';
import { MasrafTipiDeletePopupComponent } from './masraf-tipi-delete-dialog.component';

@Injectable()
export class MasrafTipiResolvePagingParams implements Resolve<any> {

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

export const masrafTipiRoute: Routes = [
    {
        path: 'masraf-tipi',
        component: MasrafTipiComponent,
        resolve: {
            'pagingParams': MasrafTipiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MasrafTipis'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'masraf-tipi/:id',
        component: MasrafTipiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MasrafTipis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const masrafTipiPopupRoute: Routes = [
    {
        path: 'masraf-tipi-new',
        component: MasrafTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MasrafTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'masraf-tipi/:id/edit',
        component: MasrafTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MasrafTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'masraf-tipi/:id/delete',
        component: MasrafTipiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MasrafTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
