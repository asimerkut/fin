import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DosyaComponent } from './dosya.component';
import { DosyaDetailComponent } from './dosya-detail.component';
import { DosyaPopupComponent } from './dosya-dialog.component';
import { DosyaDeletePopupComponent } from './dosya-delete-dialog.component';

@Injectable()
export class DosyaResolvePagingParams implements Resolve<any> {

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

export const dosyaRoute: Routes = [
    {
        path: 'dosya',
        component: DosyaComponent,
        resolve: {
            'pagingParams': DosyaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dosyas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dosya/:id',
        component: DosyaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dosyas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dosyaPopupRoute: Routes = [
    {
        path: 'dosya-new',
        component: DosyaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dosyas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya/:id/edit',
        component: DosyaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dosyas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dosya/:id/delete',
        component: DosyaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dosyas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
