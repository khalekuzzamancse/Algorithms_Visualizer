import SwiftUI
#Preview{
    Demo()
}
struct Demo: View {
    @State private var showInputDialog = false

    var body: some View {
   
        Button("Start Visualization") {
            showInputDialog = true
        }
        .sheet(isPresented: $showInputDialog) {
            AlgorithmInputDialog { array, target in
                print("Array:", array)
                print("Target:", target)
            }
        }
    }
}

#Preview {
    Demo()
}
public struct AlgorithmInputDialog: View {

    @Environment(\.dismiss) private var dismiss

    @State private var arrayInput = "20,10,50,40,30"
    @State private var targetInput = "40"
    @State private var errorMessage: String?

    private let title: String
    private let mode: InputMode

    private let arrayHandler: (([Int]) -> Void)?
    private let arrayTargetHandler: (([Int], Int) -> Void)?

    // MARK: Array-only initializer

    public init(
        title: String = "Enter Array",
        onSubmit: @escaping ([Int]) -> Void
    ) {
        self.title = title
        self.mode = .arrayOnly
        self.arrayHandler = onSubmit
        self.arrayTargetHandler = nil
    }

    // MARK: Array and target initializer

    public init(
        title: String = "Enter Array and Target",
        onSubmit: @escaping ([Int], Int) -> Void
    ) {
        self.title = title
        self.mode = .arrayAndTarget
        self.arrayHandler = nil
        self.arrayTargetHandler = onSubmit
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 20) {

            header

            ArrayInputField(text: $arrayInput)

            if mode == .arrayAndTarget {
                TargetInputField(text: $targetInput)
            }

            Text("Separate numbers using spaces, commas, or both.")
                .font(.footnote)
                .foregroundStyle(.secondary)

            if let errorMessage {
                ErrorMessageView(message: errorMessage)
            }

            actionButtons
        }
        .padding(24)
        .presentationDetents([
            .height(mode == .arrayOnly ? 320 : 400)
        ])
        .presentationDragIndicator(.visible)
    }

    private var header: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(title)
                    .font(.title2)
                    .fontWeight(.semibold)

                Text(
                    mode == .arrayOnly
                    ? "Provide the array elements."
                    : "Provide the array and target value."
                )
                .font(.subheadline)
                .foregroundStyle(.secondary)
            }

            Spacer()

            Button {
                dismiss()
            } label: {
                Image(systemName: "xmark")
                    .fontWeight(.semibold)
                    .frame(width: 32, height: 32)
                    .background(
                        Color.secondary.opacity(0.15),
                        in: Circle()
                    )
            }
            .buttonStyle(.plain)
            .foregroundStyle(.secondary)
        }
    }

    private var actionButtons: some View {
        HStack(spacing: 12) {
            Button("Cancel") {
                dismiss()
            }
            .buttonStyle(.bordered)
            .controlSize(.large)

            Button("Start Visualization") {
                validateAndSubmit()
            }
            .buttonStyle(.borderedProminent)
            .controlSize(.large)
        }
        .frame(maxWidth: .infinity, alignment: .trailing)
    }

    private func validateAndSubmit() {
        errorMessage = nil

        guard let array = InputParser.parseArray(arrayInput) else {
            errorMessage = "Enter a valid array of integers."
            return
        }

        switch mode {
        case .arrayOnly:
            arrayHandler?(array)
            dismiss()

        case .arrayAndTarget:
            guard let target = InputParser.parseInteger(targetInput) else {
                errorMessage = "Enter a valid target integer."
                return
            }

            arrayTargetHandler?(array, target)
            dismiss()
        }
    }
}

// MARK: - Input fields

fileprivate struct ArrayInputField: View {

    @Binding var text: String

    var body: some View {
        VStack(alignment: .leading, spacing: 7) {
            Text("Array elements")
                .font(.subheadline)
                .fontWeight(.medium)

            HStack(spacing: 12) {
                Image(systemName: "list.bullet")
                    .foregroundStyle(.tint)

                TextField(
                    "Example: 10, 20, 30, 40",
                    text: $text
                )
                .keyboardType(.numbersAndPunctuation)
                .textInputAutocapitalization(.never)
                .autocorrectionDisabled()
            }
            .padding(.horizontal, 16)
            .frame(height: 52)
            .background(
                Color.secondary.opacity(0.08),
                in: RoundedRectangle(
                    cornerRadius: 14,
                    style: .continuous
                )
            )
            .overlay {
                RoundedRectangle(
                    cornerRadius: 14,
                    style: .continuous
                )
                .stroke(
                    Color.secondary.opacity(0.35),
                    lineWidth: 1
                )
            }
        }
    }
}

fileprivate struct TargetInputField: View {

    @Binding var text: String

    var body: some View {
        VStack(alignment: .leading, spacing: 7) {
            Text("Target")
                .font(.subheadline)
                .fontWeight(.medium)

            HStack(spacing: 12) {
                Image(systemName: "magnifyingglass")
                    .foregroundStyle(.tint)

                TextField(
                    "Example: 40",
                    text: $text
                )
                .keyboardType(.numbersAndPunctuation)
                .textInputAutocapitalization(.never)
                .autocorrectionDisabled()
            }
            .padding(.horizontal, 16)
            .frame(height: 52)
            .background(
                Color.secondary.opacity(0.08),
                in: RoundedRectangle(
                    cornerRadius: 14,
                    style: .continuous
                )
            )
            .overlay {
                RoundedRectangle(
                    cornerRadius: 14,
                    style: .continuous
                )
                .stroke(
                    Color.secondary.opacity(0.35),
                    lineWidth: 1
                )
            }
        }
    }
}

// MARK: - Error view

fileprivate struct ErrorMessageView: View {

    let message: String

    var body: some View {
        HStack(spacing: 8) {
            Image(systemName: "exclamationmark.triangle.fill")

            Text(message)
                .font(.footnote)
        }
        .foregroundStyle(.red)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

// MARK: - Parser

fileprivate enum InputParser {

    static func parseArray(_ input: String) -> [Int]? {
        let components = input.split { character in
            character == "," || character.isWhitespace
        }

        guard !components.isEmpty else {
            return nil
        }

        let numbers = components.compactMap {
            Int(String($0))
        }

        guard numbers.count == components.count else {
            return nil
        }

        return numbers
    }

    static func parseInteger(_ input: String) -> Int? {
        let value = input.trimmingCharacters(
            in: .whitespacesAndNewlines
        )

        return Int(value)
    }
}

// MARK: - Internal configuration

fileprivate enum InputMode {
    case arrayOnly
    case arrayAndTarget
}
