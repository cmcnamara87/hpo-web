// Modules
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// Components
import { HomeComponent } from './home/home.component';
import { ContactComponent } from './help/contact/contact.component';
import { CitationComponent } from './resources/citation/citation.component';
import { LicenseComponent } from './resources/license/license.component';
import { FaqComponent } from './resources/faq/faq.component';
import { NewsComponent } from './news/news.component';
import { OntologyComponent } from './downloads/ontology/ontology.component';
import { AnnotationsDownloadComponent } from './downloads/annotations/annotations.component';
import { DisclaimerComponent } from './resources/disclaimer/disclaimer.component';

const staticRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'citation', component: CitationComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'license', component: LicenseComponent },
  { path: 'faq', component: FaqComponent },
  { path: 'faq/:id', component: FaqComponent },
  { path:  'news', component: NewsComponent },
  { path: 'news/:id', component: NewsComponent },
  { path: 'help', loadChildren: () => import('./help/help.module').then(m => m.HelpModule) },
  { path: 'download/ontology', component: OntologyComponent },
  { path: 'download/annotation', component: AnnotationsDownloadComponent },
  { path: 'tools', loadChildren: () => import('./tools/tools.module').then(m => m.ToolsModule)},
  { path: 'disclaimer', component: DisclaimerComponent },

];
export const staticRouting = RouterModule.forChild(staticRoutes);
@NgModule({
  imports: [
    RouterModule,
    staticRouting
  ],
  exports: [ RouterModule ]
})
export class StaticRoutingModule {}
