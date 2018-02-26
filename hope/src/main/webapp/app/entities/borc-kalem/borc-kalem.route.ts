import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BorcKalemComponent } from './borc-kalem.component';
import { BorcKalemDetailComponent } from './borc-kalem-detail.component';
import { BorcKalemPopupComponent } from './borc-kalem-dialog.component';
import { BorcKalemDeletePopupComponent } from './borc-kalem-delete-dialog.component';

@Injectable()
export class BorcKalemResolvePagingParams implements Resolve<any> {

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

export const borcKalemRoute: Routes = [
    {
        path: 'borc-kalem',
        component: BorcKalemComponent,
        resolve: {
            'pagingParams': BorcKalemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcKalems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'borc-kalem/:id',
        component: BorcKalemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcKalems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const borcKalemPopupRoute: Routes = [
    {
        path: 'borc-kalem-new',
        component: BorcKalemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc-kalem/:id/edit',
        component: BorcKalemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc-kalem/:id/delete',
        component: BorcKalemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
