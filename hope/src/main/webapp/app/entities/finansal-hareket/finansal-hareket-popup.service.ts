import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FinansalHareket } from './finansal-hareket.model';
import { FinansalHareketService } from './finansal-hareket.service';

@Injectable()
export class FinansalHareketPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private finansalHareketService: FinansalHareketService

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
                this.finansalHareketService.find(id)
                    .subscribe((finansalHareketResponse: HttpResponse<FinansalHareket>) => {
                        const finansalHareket: FinansalHareket = finansalHareketResponse.body;
                        if (finansalHareket.islemKabulTarihi) {
                            finansalHareket.islemKabulTarihi = {
                                year: finansalHareket.islemKabulTarihi.getFullYear(),
                                month: finansalHareket.islemKabulTarihi.getMonth() + 1,
                                day: finansalHareket.islemKabulTarihi.getDate()
                            };
                        }
                        this.ngbModalRef = this.finansalHareketModalRef(component, finansalHareket);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.finansalHareketModalRef(component, new FinansalHareket());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    finansalHareketModalRef(component: Component, finansalHareket: FinansalHareket): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.finansalHareket = finansalHareket;
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
