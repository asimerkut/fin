import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DosyaBorcComponent } from './dosya-borc.component';
import { DosyaBorcDetailComponent } from './dosya-borc-detail.component';
import { DosyaBorcPopupComponent } from './dosya-borc-dialog.component';
import { DosyaBorcDeletePopupComponent } from './dosya-borc-delete-dialog.component';

@Injectable()
export class DosyaBorcResolvePagingParams implements Resolve<any> {

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

export const dosyaBorcRoute: Routes = [
    {
        path: 'dosya-borc',
        component: DosyaBorcComponent,
        resolve: {
            'pagingParams': DosyaBorcResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dosya-borc/:id',
        component: DosyaBorcDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dosyaBorcPopupRoute: Routes = [
    {
        path: 'dosya-borc-new',
        component: DosyaBorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya-borc/:id/edit',
        component: DosyaBorcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya-borc/:id/delete',
        component: DosyaBorcDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
