import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DosyaBorcKalemComponent } from './dosya-borc-kalem.component';
import { DosyaBorcKalemDetailComponent } from './dosya-borc-kalem-detail.component';
import { DosyaBorcKalemPopupComponent } from './dosya-borc-kalem-dialog.component';
import { DosyaBorcKalemDeletePopupComponent } from './dosya-borc-kalem-delete-dialog.component';

@Injectable()
export class DosyaBorcKalemResolvePagingParams implements Resolve<any> {

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

export const dosyaBorcKalemRoute: Routes = [
    {
        path: 'dosya-borc-kalem',
        component: DosyaBorcKalemComponent,
        resolve: {
            'pagingParams': DosyaBorcKalemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dosya-borc-kalem/:id',
        component: DosyaBorcKalemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dosyaBorcKalemPopupRoute: Routes = [
    {
        path: 'dosya-borc-kalem-new',
        component: DosyaBorcKalemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya-borc-kalem/:id/edit',
        component: DosyaBorcKalemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya-borc-kalem/:id/delete',
        component: DosyaBorcKalemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaBorcKalems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
