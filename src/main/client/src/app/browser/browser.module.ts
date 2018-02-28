// Modules
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http'
import { BrowserRoutingModule } from './browser-routing.module'
//Material Modules
import {GlobalMaterialModules} from "../shared/global.module";
// Services
import { SearchService } from './services/search/search.service';
import { TermService } from './services/term/term.service';
import { GeneService } from './services/gene/gene.service';
import { DiseaseService } from './services/disease/disease.service';
// Components
import { SearchComponent } from './pages/search/search.component';
import { TermComponent } from './pages/term/term.component';
import { DiseaseComponent } from './pages/disease/disease.component';
import { GeneComponent } from './pages/gene/gene.component';
import { BrowserComponent } from './browser.component';
// Custom Pipes
import { SortPipe } from './pipes/sort-pipe';
import { HighlightPipe} from "./pipes/highlight.pipe";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    BrowserRoutingModule,
    GlobalMaterialModules,
  ],
  providers: [ SearchService, TermService, GeneService, DiseaseService ],
  declarations: [ SearchComponent, TermComponent, DiseaseComponent,
    GeneComponent, BrowserComponent, SortPipe, HighlightPipe ]
})
export class BrowserHPOModule { }
