import { Component, OnInit, OnDestroy, Inject, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DefPivot } from './def-pivot.model';
import { DefPivotService } from './def-pivot.service';

import 'jquery/dist/jquery.min.js';
import 'jquery-ui-dist/jquery-ui.min.js';
import 'pivottable/dist/pivot.min.js';
import 'pivottable/dist/pivot.min.css';

declare var jQuery: any;
declare var $: any;

@Component({
    selector: 'jhi-def-pivot-detail',
    templateUrl: './def-pivot-detail.component.html'
})
export class DefPivotDetailComponent implements OnInit, OnDestroy, AfterViewInit {

    pivotEL: ElementRef;
    pivotId: any;

    defPivot: DefPivot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private defPivotService: DefPivotService,
        private route: ActivatedRoute,
        @Inject(ElementRef)el: ElementRef
    ) {
        this.pivotEL = el;
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDefPivots();
    }

    load(id) {
        this.pivotId = id;
        this.defPivotService.find(id)
            .subscribe((defPivotResponse: HttpResponse<DefPivot>) => {
                this.defPivot = defPivotResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDefPivots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'defPivotListModification',
            (response) => this.load(this.defPivot.id)
        );
    }
    ngAfterViewInit() {
        if (!this.pivotEL ||
            !this.pivotEL.nativeElement ||
            !this.pivotEL.nativeElement.children) {
            console.log('cant build without element');
            return;
        }
        const container = this.pivotEL.nativeElement;
        const inst = jQuery(container);
        const targetElement  = inst.find('#pivotElement');
        if (!targetElement) {
            console.log('cant find the pivot element');
            return;
        }
        while (targetElement.firstChild) {
            targetElement.removeChild(targetElement.firstChild);
        }
        console.log(targetElement);

        this.defPivotService.getPivotData(this.pivotId)
            .subscribe((defPivotResponse: HttpResponse<any>) => {
                const rest = defPivotResponse.body;
                const fieldSet = rest.fieldSet;
                const dataList = rest.dataList;
                const pvtValJson = this.defPivot.pvtVal === null ? [] : this.defPivot.pvtVal.split(',');
                const pvtColJson = this.defPivot.pvtCol === null ? [] : this.defPivot.pvtCol.split(',');
                const pvtRowJson = this.defPivot.pvtRow === null ? [] : this.defPivot.pvtRow.split(',');
                $('#pivotElement').pivotUI(
                    dataList,
                    {
                        rows: pvtRowJson,
                        cols: pvtColJson,
                        vals: pvtValJson,
                        rendererName : 'Table',
                        aggregatorName : 'Sum'
                    }
                );
            });
    }
}
