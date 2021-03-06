/*
 * Copyright (c) 2017 Univocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.license.details;

import com.univocity.api.*;
import com.univocity.api.common.*;
import com.univocity.api.license.*;

import static com.univocity.api.license.details.ProductVariant.*;

/**
 * Information required from a product to enable license validation - both online and offline.
 *
 * Use method {@link #licenseManager()} to assign and validate licenses for this product..
 */
public class Product {

	private final Long id;
	private final String name;
	private final String publicKey;
	private final ProductVariant variant;
	private final ProductVersion version;
	private final Store store;

	private LicenseManager licenseManager;

	/**
	 * Builds a product information object with current product version and a public key for license validation
	 *
	 * @param id        the ID of this product
	 * @param name      the name of this product.
	 * @param publicKey the public key used for validating the product license.
	 * @param variant   the {@link ProductVariant} associated with the given version. If {@code null} the variant will be determined by the license.
	 * @param version   the {@link ProductVersion} of the licensed product
	 * @param store     the {@link Store} of the licensed product
	 */
	public Product(Long id, String name, String publicKey, ProductVariant variant, ProductVersion version, Store store) {
		Args.positiveOrZero(id, "Product ID");
		Args.notBlank(name, "Product name");
		Args.notNull(publicKey, "Public key");
		Args.notNull(version, "Store details of product '" + name + "'");

		this.id = id;
		this.name = name;
		this.publicKey = publicKey;
		this.variant = variant == null ? LICENSE_PROVIDED : variant;
		this.version = version;
		this.store = store;
	}

	/**
	 * Returns the name of the product.
	 *
	 * @return the name of the product
	 */
	public final String name() {
		return name;
	}

	/**
	 * The unique ID of the product.
	 *
	 * @return the ID of the product.
	 */
	public final Long id() {
		return id;
	}

	/**
	 * The public key {@code String} provided in the product settings page.
	 *
	 * @return the public key to be used for license validation.
	 */
	public final String publicKey() {
		return publicKey;
	}

	/**
	 * Returns the {@link ProductVariant} associated with the current version.
	 *
	 * @return the product variant related to this version.
	 */
	public final ProductVariant variant() {
		return variant;
	}

	/**
	 * Returns the product {@link ProductVersion} being licensed
	 *
	 * @return the licensed product version
	 */
	public final ProductVersion version() {
		return version;
	}

	/**
	 * Returns the {@link Store} of the store that sells licenses for this product.
	 *
	 * @return the current product store details.
	 */
	public final Store store() {
		return store;
	}

	/**
	 * Returns the {@link LicenseManager} used to manage and validate licenses associated with this product.
	 *
	 * @return the license manager of this product.
	 */
	public synchronized final LicenseManager licenseManager() {
		if (licenseManager == null) {
			licenseManager = Builder.build(LicenseManager.class, this);
		}
		return licenseManager;
	}

	@Override
	public final String toString() {
		if (variant.description().isEmpty()) {
			return name + ' ' + variant.description() + ' ' + version.identifier();
		} else {
			return name + ' ' + version.identifier();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Product product = (Product) o;

		if (!id.equals(product.id)) return false;
		if (!name.equals(product.name)) return false;
		if (!publicKey.equals(product.publicKey)) return false;
		if (variant != null ? !variant.equals(product.variant) : product.variant != null) return false;
		if (!version.equals(product.version)) return false;
		return store.equals(product.store);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + publicKey.hashCode();
		result = 31 * result + (variant != null ? variant.hashCode() : 0);
		result = 31 * result + version.hashCode();
		result = 31 * result + store.hashCode();
		return result;
	}
}
