export interface VerificationCase {
  caseId: string;
  caseReference: string;
  status: string;
  customerName: string;
  createdAt: string;
  completedAt?: string;
  steps: VerificationStep[];
  verificationResults?: Record<string, any>;
  riskScore?: number;
}

export interface VerificationStep {
  stepId: string;
  stepType: string;
  status: string;
  stepOrder: number;
  assignedTo?: string;
  startDate?: string;
  completionDate?: string;
  notes?: string;
}

export interface VerificationRequest {
  customerId: string;
  documentIds?: string[];
  notes?: string;
}

export interface VerificationResponse {
  caseId: string;
  status: string;
  message?: string;
  data?: Record<string, any>;
}
