import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DefItem } from './def-item.model';
import { DefItemService } from './def-item.service';
import { Principal } from '../../shared';
import {ComboSelModel} from '../common/combo-sel-model';
import {DefType, DefTypeService} from '../def-type';

@Component({
    selector: 'jhi-def-item',
    templateUrl: './def-item.component.html'
})
export class DefItemComponent implements OnInit, OnDestroy {
defItems: DefItem[];
    comboSelModel: ComboSelModel = new ComboSelModel();
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private defItemService: DefItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private defTypeService: DefTypeService

    ) {
        // this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
        //    this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        console.log(this.comboSelModel.comboSel);
        const param = {
            selId: this.comboSelModel.comboSel == null ? 0 : this.comboSelModel.comboSel.id
        };
        this.currentSearch = JSON.stringify(param);
        this.defItemService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<DefItem[]>) => this.defItems = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
    }

    search() {
        this.loadAll();
    }

    clear() {
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.defTypeService.query()
            .subscribe((res: HttpResponse<DefType[]>) => { this.comboSelModel.comboList = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.comboSelModel.comboSel = null;
        this.registerChangeInDefItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DefItem) {
        return item.id;
    }
    registerChangeInDefItems() {
        this.eventSubscriber = this.eventManager.subscribe('defItemListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    onChange($event) {
        this.search();
    }
}
