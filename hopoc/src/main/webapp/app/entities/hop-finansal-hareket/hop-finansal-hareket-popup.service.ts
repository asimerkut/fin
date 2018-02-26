import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { HopFinansalHareket } from './hop-finansal-hareket.model';
import { HopFinansalHareketService } from './hop-finansal-hareket.service';

@Injectable()
export class HopFinansalHareketPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hopFinansalHareketService: HopFinansalHareketService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.hopFinansalHareketService.find(id)
                    .subscribe((hopFinansalHareketResponse: HttpResponse<HopFinansalHareket>) => {
                        const hopFinansalHareket: HopFinansalHareket = hopFinansalHareketResponse.body;
                        if (hopFinansalHareket.tarih) {
                            hopFinansalHareket.tarih = {
                                year: hopFinansalHareket.tarih.getFullYear(),
                                month: hopFinansalHareket.tarih.getMonth() + 1,
                                day: hopFinansalHareket.tarih.getDate()
                            };
                        }
                        this.ngbModalRef = this.hopFinansalHareketModalRef(component, hopFinansalHareket);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hopFinansalHareketModalRef(component, new HopFinansalHareket());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hopFinansalHareketModalRef(component: Component, hopFinansalHareket: HopFinansalHareket): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hopFinansalHareket = hopFinansalHareket;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
