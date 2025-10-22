# 🧠 GraphManager

**GraphManager** is a lightweight Java framework for creating, traversing, and managing **semantic graphs** using the [SLIB](https://github.com/sharispe/slib) library and [Apache Jena](https://jena.apache.org/).  
It enables developers and researchers to generate RDF-based semantic graphs, explore Linked Data resources, and construct semantic connections dynamically.

---

## 🧩 Overview

This project provides two core classes:

| Class | Description |
|--------|-------------|
| **`GraphManager`** | Provides core utilities to create semantic graphs, inspect vertices and edges, and perform graph manipulations using the SLIB graph model. |
| **`LdResources`** | Provides SPARQL-based Linked Data graph expansion methods, enabling traversal of resources (via DBpedia or a custom dataset) to populate a semantic graph. |

Together, these components form a modular base for building **semantic graph exploration tools**, **knowledge graph construction pipelines**, or **ontology-driven applications**.

---

## 🚀 Features

- 🔧 Create in-memory semantic graphs (`GraphMemory`)
- 🌐 Query and traverse Linked Data (via SPARQL and DBpedia)
- 🔁 Explore incoming/outgoing relationships
- 🧭 Find connecting paths between two resources
- 🧹 Automatically avoid re-traversing visited URIs
- 🗑️ Manage and remove vertices and edges dynamically
- 💾 Support for both remote (`http://dbpedia.org/sparql`) and local `Dataset` traversal

---

## ⚙️ Requirements

- **Java 8+**
- **Apache Jena** (for SPARQL and RDF querying)
- **SLIB Graph Library**

Example Maven dependencies:

```xml
<dependencies>
    <!-- Apache Jena -->
    <dependency>
        <groupId>org.apache.jena</groupId>
        <artifactId>apache-jena-libs</artifactId>
        <version>4.10.0</version>
        <type>pom</type>
    </dependency>

    <!-- SLIB Graph Library -->
    <dependency>
        <groupId>com.github.sharispe</groupId>
        <artifactId>slib</artifactId>
        <version>0.9.1</version>
    </dependency>
</dependencies>
