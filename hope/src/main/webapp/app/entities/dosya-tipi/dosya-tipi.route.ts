import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DosyaTipiComponent } from './dosya-tipi.component';
import { DosyaTipiDetailComponent } from './dosya-tipi-detail.component';
import { DosyaTipiPopupComponent } from './dosya-tipi-dialog.component';
import { DosyaTipiDeletePopupComponent } from './dosya-tipi-delete-dialog.component';

@Injectable()
export class DosyaTipiResolvePagingParams implements Resolve<any> {

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

export const dosyaTipiRoute: Routes = [
    {
        path: 'dosya-tipi',
        component: DosyaTipiComponent,
        resolve: {
            'pagingParams': DosyaTipiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaTipis'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dosya-tipi/:id',
        component: DosyaTipiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaTipis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dosyaTipiPopupRoute: Routes = [
    {
        path: 'dosya-tipi-new',
        component: DosyaTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya-tipi/:id/edit',
        component: DosyaTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya-tipi/:id/delete',
        component: DosyaTipiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DosyaTipis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
