UPDATE posted_company
SET
    status = /* postedCompany.status */1,
    updatedBy = /* postedCompany.updatedBy */"manual",
/* %if postedCompany.entryDate != null */
    entryDate = /* postedCompany.entryDate*/"2022-01-01"
/* %end */
WHERE
    userId = /* postedCompany.userId */1,
    companyId = /* postedCompany.companyId */1