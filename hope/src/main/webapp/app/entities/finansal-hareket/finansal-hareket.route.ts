import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FinansalHareketComponent } from './finansal-hareket.component';
import { FinansalHareketDetailComponent } from './finansal-hareket-detail.component';
import { FinansalHareketPopupComponent } from './finansal-hareket-dialog.component';
import { FinansalHareketDeletePopupComponent } from './finansal-hareket-delete-dialog.component';

@Injectable()
export class FinansalHareketResolvePagingParams implements Resolve<any> {

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

export const finansalHareketRoute: Routes = [
    {
        path: 'finansal-hareket',
        component: FinansalHareketComponent,
        resolve: {
            'pagingParams': FinansalHareketResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHarekets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'finansal-hareket/:id',
        component: FinansalHareketDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHarekets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const finansalHareketPopupRoute: Routes = [
    {
        path: 'finansal-hareket-new',
        component: FinansalHareketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHarekets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'finansal-hareket/:id/edit',
        component: FinansalHareketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHarekets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'finansal-hareket/:id/delete',
        component: FinansalHareketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinansalHarekets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
