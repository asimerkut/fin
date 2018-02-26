import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BorcGrubuComponent } from './borc-grubu.component';
import { BorcGrubuDetailComponent } from './borc-grubu-detail.component';
import { BorcGrubuPopupComponent } from './borc-grubu-dialog.component';
import { BorcGrubuDeletePopupComponent } from './borc-grubu-delete-dialog.component';

@Injectable()
export class BorcGrubuResolvePagingParams implements Resolve<any> {

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

export const borcGrubuRoute: Routes = [
    {
        path: 'borc-grubu',
        component: BorcGrubuComponent,
        resolve: {
            'pagingParams': BorcGrubuResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcGrubus'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'borc-grubu/:id',
        component: BorcGrubuDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcGrubus'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const borcGrubuPopupRoute: Routes = [
    {
        path: 'borc-grubu-new',
        component: BorcGrubuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcGrubus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc-grubu/:id/edit',
        component: BorcGrubuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcGrubus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'borc-grubu/:id/delete',
        component: BorcGrubuDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BorcGrubus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
