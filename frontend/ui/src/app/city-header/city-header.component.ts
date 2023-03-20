import {Component, EventEmitter, Output} from '@angular/core';
import {categoryMap} from "../const-variables";

@Component({
  selector: 'app-city-header',
  templateUrl: './city-header.component.html',
  styleUrls: ['./city-header.component.css']
})
export class CityHeaderComponent {

  readonly categories = ['Bar', 'Restaurant', 'Coffee bar', 'Night club'];
  selectedCategory = '';
  enteredSearchValue = '';
  @Output() filterOut = new EventEmitter<string>();
  @Output() searchTextChanged = new EventEmitter<string>();

  onSearchTextChanged() {
    this.searchTextChanged.emit(this.enteredSearchValue);
  }

  filterRestaurants(category: string): void {
    if (this.selectedCategory !== category) {
      this.selectedCategory = category;
    } else {
      this.selectedCategory = '';
    }
    this.filterOut.emit(this.selectedCategory);
  }

  getActiveClass(category: string): string {
    return this.selectedCategory === category ? 'nav-item active' : 'nav-item';
  }

  getButtonStyle(category: string): string {
    return this.selectedCategory === category ? 'gray' : '';
  }

  getCirylicCategoryName(category: string) {
    return categoryMap.get(category);
  }
}
