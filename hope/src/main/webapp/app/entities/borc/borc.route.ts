import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BorcComponent } from './borc.component';
import { BorcDetailComponent } from './borc-detail.component';
import { BorcPopupComponent } from './borc-dialog.component';
import { BorcDeletePopupComponent } from './borc-delete-dialog.component';

@Injectable()
export class BorcResolvePagingParams implements Resolve<any> {

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

export const borcRoute: Routes = [
    {
        path: 'borc',
        component: BorcComponent,
        resolve: {
            'pagingParams': BorcResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Borcs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'borc/:id',
        component: BorcDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Borcs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const borcPopupRoute: Routes = [
    {
        path: 'borc-new',
        component: BorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Borcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc/:id/edit',
        component: BorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Borcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc/:id/delete',
        component: BorcDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Borcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
