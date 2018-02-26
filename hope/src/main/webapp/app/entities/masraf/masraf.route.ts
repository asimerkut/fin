import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MasrafComponent } from './masraf.component';
import { MasrafDetailComponent } from './masraf-detail.component';
import { MasrafPopupComponent } from './masraf-dialog.component';
import { MasrafDeletePopupComponent } from './masraf-delete-dialog.component';

@Injectable()
export class MasrafResolvePagingParams implements Resolve<any> {

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

export const masrafRoute: Routes = [
    {
        path: 'masraf',
        component: MasrafComponent,
        resolve: {
            'pagingParams': MasrafResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Masrafs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'masraf/:id',
        component: MasrafDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Masrafs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const masrafPopupRoute: Routes = [
    {
        path: 'masraf-new',
        component: MasrafPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Masrafs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'masraf/:id/edit',
        component: MasrafPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Masrafs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'masraf/:id/delete',
        component: MasrafDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Masrafs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
