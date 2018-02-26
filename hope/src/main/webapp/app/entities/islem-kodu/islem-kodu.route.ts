import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IslemKoduComponent } from './islem-kodu.component';
import { IslemKoduDetailComponent } from './islem-kodu-detail.component';
import { IslemKoduPopupComponent } from './islem-kodu-dialog.component';
import { IslemKoduDeletePopupComponent } from './islem-kodu-delete-dialog.component';

@Injectable()
export class IslemKoduResolvePagingParams implements Resolve<any> {

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

export const islemKoduRoute: Routes = [
    {
        path: 'islem-kodu',
        component: IslemKoduComponent,
        resolve: {
            'pagingParams': IslemKoduResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IslemKodus'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'islem-kodu/:id',
        component: IslemKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IslemKodus'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const islemKoduPopupRoute: Routes = [
    {
        path: 'islem-kodu-new',
        component: IslemKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IslemKodus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'islem-kodu/:id/edit',
        component: IslemKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IslemKodus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'islem-kodu/:id/delete',
        component: IslemKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IslemKodus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
