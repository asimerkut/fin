import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { DosyaBorcKalem } from './dosya-borc-kalem.model';
import { DosyaBorcKalemService } from './dosya-borc-kalem.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {ComboSelModel} from '../common/combo-sel-model';
import {Dosya, DosyaService} from '../dosya';

@Component({
    selector: 'jhi-dosya-borc-kalem',
    templateUrl: './dosya-borc-kalem.component.html'
})
export class DosyaBorcKalemComponent implements OnInit, OnDestroy {

currentAccount: any;
    dosyaBorcKalems: DosyaBorcKalem[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    comboSelModel: ComboSelModel = new ComboSelModel();

    constructor(
        private dosyaBorcKalemService: DosyaBorcKalemService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private dosyaService: DosyaService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.dosyaBorcKalemService.search({
                page: this.page - 1,
                query: this.currentSearch,
                size: this.itemsPerPage,
                sort: this.sort()}).subscribe(
                    (res: HttpResponse<DosyaBorcKalem[]>) => this.onSuccess(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.dosyaBorcKalemService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<DosyaBorcKalem[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/dosya-borc-kalem'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate(['/dosya-borc-kalem', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate(['/dosya-borc-kalem', {
            search: this.currentSearch,
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.dosyaService.query()
            .subscribe((res: HttpResponse<Dosya[]>) => { this.comboSelModel.comboList = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.comboSelModel.comboSel = null;
        this.registerChangeInDosyaBorcKalems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DosyaBorcKalem) {
        return item.id;
    }
    registerChangeInDosyaBorcKalems() {
        this.eventSubscriber = this.eventManager.subscribe('dosyaBorcKalemListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.dosyaBorcKalems = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    onChange($event) {
        // console.log($event.srcElement.selectedOptions[0].getAttribute('ng-reflect-ng-value'));
        console.log(this.comboSelModel.comboSel);
        const param = {
            selId: this.comboSelModel.comboSel.id
        };
        const str: String = JSON.stringify(param);
        this.search(str);
    }
}
