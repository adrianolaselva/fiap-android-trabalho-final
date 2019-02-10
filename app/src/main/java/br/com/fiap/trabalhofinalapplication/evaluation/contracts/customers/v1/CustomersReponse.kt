package br.com.fiap.trabalhofinalapplication.evaluation.contracts.customers.v1

import br.com.fiap.trabalhofinalapplication.evaluation.contracts.Customer
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.Sort

data class CustomersReponse(
    val content: ArrayList<Customer>? = null,
    val totalPages: Int = 0,
    val totalElements: Int = 0,
    val last: Boolean? = null,
    val first: Boolean? = null,
    val sort: Sort? = null,
    val numberOfElements: Int = 0,
    val size: Int = 0,
    val number: Int = 0,
    val empty: Boolean? = null)