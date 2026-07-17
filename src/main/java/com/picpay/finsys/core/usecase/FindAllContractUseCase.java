package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindAllContractUseCase {
    Page<ContractDomain> execute(Pageable page);
}
