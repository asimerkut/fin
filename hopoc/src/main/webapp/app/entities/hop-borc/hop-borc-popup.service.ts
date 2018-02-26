import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { HopBorc } from './hop-borc.model';
import { HopBorcService } from './hop-borc.service';

@Injectable()
export class HopBorcPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hopBorcService: HopBorcService

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
                this.hopBorcService.find(id)
                    .subscribe((hopBorcResponse: HttpResponse<HopBorc>) => {
                        const hopBorc: HopBorc = hopBorcResponse.body;
                        if (hopBorc.tarih) {
                            hopBorc.tarih = {
                                year: hopBorc.tarih.getFullYear(),
                                month: hopBorc.tarih.getMonth() + 1,
                                day: hopBorc.tarih.getDate()
                            };
                        }
                        this.ngbModalRef = this.hopBorcModalRef(component, hopBorc);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hopBorcModalRef(component, new HopBorc());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hopBorcModalRef(component: Component, hopBorc: HopBorc): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hopBorc = hopBorc;
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
