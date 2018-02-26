import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { HopDosyaBorcKalem } from './hop-dosya-borc-kalem.model';
import { HopDosyaBorcKalemService } from './hop-dosya-borc-kalem.service';

@Injectable()
export class HopDosyaBorcKalemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hopDosyaBorcKalemService: HopDosyaBorcKalemService

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
                this.hopDosyaBorcKalemService.find(id)
                    .subscribe((hopDosyaBorcKalemResponse: HttpResponse<HopDosyaBorcKalem>) => {
                        const hopDosyaBorcKalem: HopDosyaBorcKalem = hopDosyaBorcKalemResponse.body;
                        this.ngbModalRef = this.hopDosyaBorcKalemModalRef(component, hopDosyaBorcKalem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hopDosyaBorcKalemModalRef(component, new HopDosyaBorcKalem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hopDosyaBorcKalemModalRef(component: Component, hopDosyaBorcKalem: HopDosyaBorcKalem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hopDosyaBorcKalem = hopDosyaBorcKalem;
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
