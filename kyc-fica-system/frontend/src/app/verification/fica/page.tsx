'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Card } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import apiClient from '@/lib/api/apiClient';
import type { VerificationCase } from '@/types/verification';



export default function FicaVerification() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(false);
  const [currentStep, setCurrentStep] = useState(1);
  const [verificationCase, setVerificationCase] = useState<VerificationCase | null>(null);
  const [formData, setFormData] = useState({
    customerId: '',
    documentIds: [],
    notes: ''
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await apiClient.post('/api/verification/fica/initiate', formData);
      setVerificationCase(response.data);
      setCurrentStep(2);
    } catch (error) {
      console.error('Error initiating FICA verification:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleVerifyFinancialInformation = async () => {
    if (!verificationCase?.caseId) return;
    setIsLoading(true);

    try {
      const response = await apiClient.post(`/api/verification/fica/${verificationCase.caseId}/verify-financial-information`);
      console.log('Financial information verification results:', response.data);
      setCurrentStep(3);
    } catch (error) {
      console.error('Error verifying financial information:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleVerifyIncomeSources = async () => {
    if (!verificationCase?.caseId) return;
    setIsLoading(true);

    try {
      const response = await apiClient.post(`/api/verification/fica/${verificationCase.caseId}/verify-income-sources`);
      console.log('Income sources verification results:', response.data);
      setCurrentStep(4);
    } catch (error) {
      console.error('Error verifying income sources:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleVerifyTransactionHistory = async () => {
    if (!verificationCase?.caseId) return;
    setIsLoading(true);

    try {
      const response = await apiClient.post(`/api/verification/fica/${verificationCase.caseId}/verify-transaction-history`);
      console.log('Transaction history verification results:', response.data);
      setCurrentStep(5);
    } catch (error) {
      console.error('Error verifying transaction history:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleComplete = async (approved: boolean) => {
    if (!verificationCase?.caseId) return;
    setIsLoading(true);

    try {
      const response = await apiClient.post(`/api/verification/fica/${verificationCase.caseId}/complete`, {
        approved,
        notes: 'Verification completed successfully'
      });
      console.log('Verification completed:', response.data);
      router.push('/dashboard');
    } catch (error) {
      console.error('Error completing verification:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto py-8">
      <h1 className="text-3xl font-bold mb-8">FICA Verification</h1>

      <div className="space-y-8">
        {/* Step 1: Initiate Verification */}
        <Card className={`p-6 ${currentStep === 1 ? '' : 'opacity-50'}`}>
          <h2 className="text-xl font-semibold mb-4">Step 1: Initiate Verification</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="customerId" className="form-label">Customer ID</label>
              <input
                type="text"
                id="customerId"
                className="form-input"
                value={formData.customerId}
                onChange={(e) => setFormData({ ...formData, customerId: e.target.value })}
                required
              />
            </div>
            <div>
              <label htmlFor="notes" className="form-label">Notes</label>
              <textarea
                id="notes"
                className="form-input"
                value={formData.notes}
                onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
              />
            </div>
            <Button
              type="submit"
              disabled={isLoading || currentStep !== 1}
            >
              {isLoading ? 'Initiating...' : 'Start Verification'}
            </Button>
          </form>
        </Card>

        {/* Step 2: Financial Information Verification */}
        <Card className={`p-6 ${currentStep === 2 ? '' : 'opacity-50'}`}>
          <h2 className="text-xl font-semibold mb-4">Step 2: Financial Information Verification</h2>
          <Button
            onClick={handleVerifyFinancialInformation}
            disabled={isLoading || currentStep !== 2}
          >
            {isLoading ? 'Verifying...' : 'Verify Financial Information'}
          </Button>
        </Card>

        {/* Step 3: Income Sources Verification */}
        <Card className={`p-6 ${currentStep === 3 ? '' : 'opacity-50'}`}>
          <h2 className="text-xl font-semibold mb-4">Step 3: Income Sources Verification</h2>
          <Button
            onClick={handleVerifyIncomeSources}
            disabled={isLoading || currentStep !== 3}
          >
            {isLoading ? 'Verifying...' : 'Verify Income Sources'}
          </Button>
        </Card>

        {/* Step 4: Transaction History Verification */}
        <Card className={`p-6 ${currentStep === 4 ? '' : 'opacity-50'}`}>
          <h2 className="text-xl font-semibold mb-4">Step 4: Transaction History Verification</h2>
          <Button
            onClick={handleVerifyTransactionHistory}
            disabled={isLoading || currentStep !== 4}
          >
            {isLoading ? 'Verifying...' : 'Verify Transaction History'}
          </Button>
        </Card>

        {/* Step 5: Complete Verification */}
        <Card className={`p-6 ${currentStep === 5 ? '' : 'opacity-50'}`}>
          <h2 className="text-xl font-semibold mb-4">Step 5: Complete Verification</h2>
          <div className="space-x-4">
            <Button
              variant="primary"
              onClick={() => handleComplete(true)}
              disabled={isLoading || currentStep !== 5}
            >
              {isLoading ? 'Approving...' : 'Approve'}
            </Button>
            <Button
              variant="destructive"
              onClick={() => handleComplete(false)}
              disabled={isLoading || currentStep !== 5}
            >
              {isLoading ? 'Rejecting...' : 'Reject'}
            </Button>
          </div>
        </Card>
      </div>
    </div>
  );
}
