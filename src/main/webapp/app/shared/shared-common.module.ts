import { NgModule } from '@angular/core';

import { ArtistsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ArtistsSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ArtistsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ArtistsSharedCommonModule {}
