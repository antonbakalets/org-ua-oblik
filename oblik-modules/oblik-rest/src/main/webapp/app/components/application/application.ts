import {Component, ViewEncapsulation} from "@angular/core";
import {Product, ProductService} from "../../services/product-service";

@Component({
    selector: 'oblik-application',
    templateUrl: 'app/components/application/application.html',
    styleUrls: ['app/components/application/application.css'],
    encapsulation: ViewEncapsulation.None
})

export default class ApplicationComponent {
    products: Array<Product> = [];
    total: number;

    constructor(private productService: ProductService) {
        this.products = this.productService.getProducts();
        this.total = this.productService.getTotal();
    }
}



