import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Masraf } from './masraf.model';
import { MasrafService } from './masraf.service';

@Injectable()
export class MasrafPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private masrafService: MasrafService

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
                this.masrafService.find(id)
                    .subscribe((masrafResponse: HttpResponse<Masraf>) => {
                        const masraf: Masraf = masrafResponse.body;
                        if (masraf.masrafTarihi) {
                            masraf.masrafTarihi = {
                                year: masraf.masrafTarihi.getFullYear(),
                                month: masraf.masrafTarihi.getMonth() + 1,
                                day: masraf.masrafTarihi.getDate()
                            };
                        }
                        this.ngbModalRef = this.masrafModalRef(component, masraf);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.masrafModalRef(component, new Masraf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    masrafModalRef(component: Component, masraf: Masraf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.masraf = masraf;
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
