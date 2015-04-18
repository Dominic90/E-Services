class CreateConnections < ActiveRecord::Migration
  def change
    create_table :connections do |t|
      t.string :ip
      t.integer :port
      t.boolean :used

      t.timestamps null: false
    end
  end
end
