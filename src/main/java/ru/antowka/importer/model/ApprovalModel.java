package ru.antowka.importer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ApprovalModel {

    private String approverRef;

    private Set<ApprovalModel> internalApprovalData;
}
