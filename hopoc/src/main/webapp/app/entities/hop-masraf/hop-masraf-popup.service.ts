import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { HopMasraf } from './hop-masraf.model';
import { HopMasrafService } from './hop-masraf.service';

@Injectable()
export class HopMasrafPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hopMasrafService: HopMasrafService

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
                this.hopMasrafService.find(id)
                    .subscribe((hopMasrafResponse: HttpResponse<HopMasraf>) => {
                        const hopMasraf: HopMasraf = hopMasrafResponse.body;
                        if (hopMasraf.tarih) {
                            hopMasraf.tarih = {
                                year: hopMasraf.tarih.getFullYear(),
                                month: hopMasraf.tarih.getMonth() + 1,
                                day: hopMasraf.tarih.getDate()
                            };
                        }
                        this.ngbModalRef = this.hopMasrafModalRef(component, hopMasraf);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hopMasrafModalRef(component, new HopMasraf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hopMasrafModalRef(component: Component, hopMasraf: HopMasraf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hopMasraf = hopMasraf;
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
